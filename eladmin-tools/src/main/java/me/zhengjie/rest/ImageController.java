/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageQueryParam;
import me.zhengjie.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "工具：获取图片")
@RequestMapping("/api/pic")
public class ImageController {

    private final LocalStorageService localStorageService;

    @ApiOperation("查询图片")
    @GetMapping
    @AnonymousAccess
    public void viewPic(Long id, HttpServletResponse response) throws IOException {
        LocalStorage localStorage = localStorageService.getById(id);
        OutputStream os = null;
        try {
//        读取图片
            BufferedImage image = ImageIO.read(new FileInputStream(new File(localStorage.getPath())));
            response.setContentType("image/png");
            os = response.getOutputStream();

            if (image != null) {
                ImageIO.write(image, localStorage.getSuffix(), os);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

}
