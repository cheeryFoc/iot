package com.hxk.demo.module.system.service;

import com.hxk.demo.exception.ServiceException;
import com.hxk.demo.exception.common.CodeEnum;
import com.hxk.demo.exception.common.CommonException;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: demo
 * @package: com.hxk.demo.module.system.service
 * @className: LogService
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
@Service
public class LogService {
    Logger logger = LoggerFactory.getLogger(getClass());

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        File dir = new File("./logs");
        List<File> fileList = (List<File>) FileUtils.listFiles(dir, null, false);
        fileList.stream().forEach(file -> list.add(file.getName()));
        return list;
    }

    public void deleteFile(String fileName) {
        if (fileName == null) {
            throw new ServiceException(CodeEnum.IS_NULL.getMsg(), new CommonException(CodeEnum.IS_NULL));
        }
        if ("sys-error.txt".equals(fileName) || "sys-info.txt".equals(fileName) || "sys-user.txt".equals(fileName)) {
            throw new ServiceException(CodeEnum.NOT_SUPPORT_DEL.getMsg(), new CommonException(CodeEnum.NOT_SUPPORT_DEL));
        }
        try {
            FileUtils.forceDelete(new File("./logs/" +fileName));
        } catch (Exception e) {
            throw new ServiceException("删除失败", e);
        }
    }


    public void fileDownLoad(String fileName, HttpServletResponse response) {
        logger.info("1");
        File file = new File("./logs/" + fileName);
        if(!file.exists()){
            throw new ServiceException("文件不存在", new FileExistsException());
        }
        logger.info("2");
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new ServiceException("文件下载失败", e);
        }
    }
}
