package com.whu.controller.center;

import com.whu.controller.BaseController;
import com.whu.pojo.Users;
import com.whu.pojo.bo.center.CenterUserBO;
import com.whu.resource.FileUpload;
import com.whu.service.center.CenterUserService;
import com.whu.utils.CookieUtils;
import com.whu.utils.DateUtil;
import com.whu.utils.JsonUtils;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "上传头像", notes = "上传头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public Result uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
            MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

        //定义头像保存的地址
        //String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace=fileUpload.getImageUserFaceLocation();

        //在路径下为每一个用户userid，用于区分每一个用户上传的头像
        String uploadPathPrefix = File.separator + userId;


        //开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;

            //获得文件上传的文件名称
            String fileName = file.getOriginalFilename();

            if (StringUtils.isNotBlank(fileName)) {
                //face-{userId}.png
                //文件重命名 imooc-face.png->["imooc-face","png"]
                String[] fileNameArr = fileName.split("\\.");

                //获取文件的后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];

                //判断文件的后缀名，防止恶意文件的上传
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")) {
                    return Result.errorMsg("图片格式不正确!");
                }

                //文件名称重组
                //文件名称重组 覆盖式上传 增量式:额外拼接当前时间
                String newFileName = "face-" + userId + "." + suffix;

                //上传的头像最终保存的位置
                String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                //用于提供给web服务访问的地址
                uploadPathPrefix += ("/" + newFileName);

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null) {
                    //创建文件夹
                    outFile.getParentFile().mkdirs();
                }

                //文件输出到保存目录
                try {
                    fileOutputStream = new FileOutputStream(outFile);//这个是头像保存路径的FileOutputStream
                    InputStream inputStream = file.getInputStream();//这个是file的InputStream
                    IOUtils.copy(inputStream,fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            return Result.errorMsg("文件不能为空");
        }

        //获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        //在web访问用户头像的地址，由于浏览器可能存在缓存的情况，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix+
                "?t="+ DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN);

        //更新用户头像到数据库
        Users userResult=centerUserService.updateUserFace(userId,finalUserFaceUrl);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改，增加令牌token，会整合redis，分布式会话

        return Result.ok();
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public Result update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) {

        //判断BindingResult是否包含错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return Result.errorMap(errorMap);
        }

        Users userResult = centerUserService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        //TODO 后续要改，增加令牌token，会整合redis，分布式会话

        return Result.ok();
    }

    //处理错误
    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            //发生错误验证所对应的某一个
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }


    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);

        return userResult;
    }
}
