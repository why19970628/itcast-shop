package com.itheima.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class UploadUtil {

	
	
	/**
	 * 获取随机名称
	 * @param realName 真实名称
	 * @return uuid 随机名称
	 */
	public static String getUUIDName(String realName){
		//realname  可能是  1.jpg   也可能是  1
		//获取后缀名
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	public static String BaseDir(){

		ResourceBundle upload = ResourceBundle.getBundle("upload");
		return upload.getString("baseDir");
	}



	/**
	 * 获取文件目录,可以获取256个随机目录
	 * @return 随机目录 /a/4  /b/c
	 */
	public static String randDir(){
		String s="0123456789ABCDEF";
		Random r = new Random();
		return "/"+s.charAt(r.nextInt(16))+"/"+s.charAt(r.nextInt(16))+"/";
	}

	public static void mustExist(String path){
		File file = new File(path);
		if (!file.exists()){
			file.mkdirs();
		}

	}

	public static void main(String[] args) {
		String s = randDir();
		System.out.println(s);
	}

	public static Map<String, String[]> parse(HttpServletRequest req) {
		Map<String,String[]> result=new HashMap<>();

		try {
			//创建文件磁盘工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建文件上传request解析对象
			ServletFileUpload parser = new ServletFileUpload(factory);
			//使用该对象帮助你解析request对象
			List<FileItem> items = parser.parseRequest(req);
			//解析完成以后返回 表单项列表(它已经按照格式解析出来数据 取数据)

			//遍历它
			for (FileItem item : items) {
				//获取表单项的名字
				String fieldName = item.getFieldName();
				//System.out.println("参数的名字:"+fieldName);

				if(item.isFormField()){
					//如果是普通项
					String value = item.getString("utf-8");

					//System.out.println("参数的值:"+value);
					putMap(result,fieldName,value);
				}else{
					//文件项
					String fileName = item.getName();
					//System.out.println("文件的名字:"+fileName);
					InputStream inputStream = item.getInputStream();

					String baseDir=UploadUtil.BaseDir();
					String randDir = UploadUtil.randDir();
					String path=baseDir+randDir;
					UploadUtil.mustExist(path);
					//防止重名 uuid
					String uuidName = UploadUtil.getUUIDName(fileName);


					FileOutputStream outputStream = new FileOutputStream(path+ uuidName);

					IOUtils.copy(inputStream,outputStream);

					inputStream.close();
					outputStream.close();
					//请调用一下该方法 为啥
					item.delete();

					result.put(fieldName,new String[]{"resources/products"+randDir+uuidName});

				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private static void putMap(Map<String, String[]> result, String name, String value) {

		//考虑 你放的时候 hobby 有可能有多个值
		if (result.containsKey(name)){
			//有取出来原来的数组
			String[] values = result.get(name);
			ArrayList<String> list = new ArrayList<>();
			//数组放到集合里
			Collections.addAll(list,values);
			//集合添加新的值
			list.add(value);
			//在吧集合变成数组
			String[] newArray = new String[list.size()];
			list.toArray(newArray);

			//吧新数组放入到map集合中
			result.put(name,newArray);
		}else{
			result.put(name,new String[]{value});
		}



	}
}
