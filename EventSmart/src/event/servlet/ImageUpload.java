package event.servlet;

import event.dal.*;
import event.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/uploadimage")
public class ImageUpload extends HttpServlet {
	protected ImagesDao imagesDao;
	
	@Override
	public void init() throws ServletException {
		imagesDao = ImagesDao.getInstance();
	}
	
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/ImageUpload.jsp").forward(req, resp);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
        
     // 获取路径来存储文件
        String path = this.getServletContext().getRealPath("/") + "WebContent/images";
        System.out.println("图片存储路径：" + path);

        // 根据路径名创建一个 File实例
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();       // 如果不存在则创建此路径的目录
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文处理
        upload.setHeaderEncoding("utf-8");

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        System.out.println("提交的是文件 ！");
                        String fileName = item.getName();
                        System.out.println("上传的文件名：" + fileName);

                        // 获取文件名后缀, 返回 "."在文件名最后出现的索引, 就是文件后缀名
                        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        System.out.println("prefix: " + prefix);
                        // 存储的文件名根据获取的id来唯一确定, 这里测试使用 "test"
                        // id可以绑定到session或request变量等等，自己根据需要来扩展
                        String id = "test";
                        String fileSaveName = id + "." + prefix; // id.后缀
                        System.out.println("savename: " + fileSaveName);

                        // 获取文件输入流
                        InputStream inputStream = item.getInputStream();
                        // 创建文件输出流，用于向指定文件名的文件写入数据
                        FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + fileSaveName);
                        int index = 0;

                        // 从输入流读取数据的下一个字节，到末尾时返回 -1
                        while ((index = inputStream.read()) != -1) {
                            fileOutputStream.write(index);  // 将指定字节写入此文件输出流
                        }
                        
                     // 关闭流
                        inputStream.close();
                        fileOutputStream.close();
                        item.delete();

                        // 存入数据库
                        //String sql = "insert into Images(URL) values(?)";
                        // 设置图片存储的虚拟路径
                        String virtualPath = "image/" + fileSaveName;
                        //Object values[] = new Object[] {virtualPath};
                        // GeneralDao.update() 为自己写的函数，采用预编译，可以根据自己的来
                        Images pic = new Images (virtualPath);
                        imagesDao.create(pic);
                        System.out.println("success");
                    } // if (!item.isFormField())
                } // for
            } // try
        } catch (Exception e) {
            request.setAttribute("message", "错误信息: " + e.getMessage());
        }

        // 重定向到显示图片页面
        response.sendRedirect("ImageUpload.jsp");
    }

}
