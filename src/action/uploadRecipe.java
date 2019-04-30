package action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import business.IncludeBusi;
import business.PictureBusi;
import business.RecipeBusi;
import business.StepBusi;
import pojo.Include;
import pojo.RPicture;
import pojo.Recipe;
import pojo.Step;
import pojo.User;

/**
 * Servlet implementation class uploadRecipe
 */
@WebServlet("/uploadRecipe")
public class uploadRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadRecipe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		int result = 0;
		
		Recipe recipe = new Recipe();
		RecipeBusi recbus = new RecipeBusi();
		recipe.setId(recbus.InitiateOneRecipe());
		if(recipe.getId() == 0) {
			System.out.println("�ϴ�ʳ�׳�ʼ��ʧ��!");
			String html = "�ϴ�ʳ�׳�ʼ��ʧ�ܣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
			response.getWriter().write(html);
			return;
		}
		
		List<Step> steps = new ArrayList<Step>();
		
		List<String> materials = new ArrayList<String>();
		List<String> Mquantities = new ArrayList<String>();
		
		List<String> picUrls = new ArrayList<String>();
		
		//�ϴ�ͼƬ
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart) {//�ж�ǰ̨��form�Ƿ���multipart����
//			FileItemFactory factory = new DiskFileItemFactory();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//ͨ��parseRequest����form�е����������ֶΣ������浽items������
			try {
				//�����ϴ��ļ�ʱ�õ�����ʱ�ļ��Ĵ�СDiskFileItemFactory
				factory.setSizeThreshold(10485760);//������ʱ�ļ���������СΪ10MB(��λΪ�ֽ�B)
				factory.setRepository(new File("D:\\Course\\Java\\workplace\\Recipe\\uploadtemp"));//������ʱ�ļ���Ŀ¼
				
				//�����ϴ������ļ��Ĵ�С  �˴�Ϊ100MB
				upload.setSizeMax(104857600);
				
				List<FileItem> items = upload.parseRequest(request);
				//����items�е�����(item=XXX)
				Iterator<FileItem> iter = items.iterator();
				int picNo = 0;
				while(iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					//�ж�ǰ̨�ֶΣ�����ͨform���ֶΣ������ļ��ֶ�
					
					//request.getParameter()   --iter.getString
					if(item.isFormField()) {//��ͨform���ֶ��ϴ�
						if(itemName.equals("recipe_name")) {
							recipe.setName(item.getString("utf-8"));
						}else if(itemName.equals("category")) {
							recipe.setCategory(item.getString("utf-8"));
						}else if(itemName.equals("complexity")) {
							recipe.setComplexity(item.getString("utf-8"));
						}else if(itemName.equals("minute")) {
							recipe.setMinute(Integer.parseInt(item.getString("utf-8")));
						}else if(itemName.equals("tasty")) {
							recipe.setTasty(item.getString("utf-8"));
						}else if(itemName.equals("method")) {
							recipe.setMethod(item.getString("utf-8"));
						}else if(itemName.equals("summary")) {
							recipe.setDescription(item.getString("utf-8"));
						}else if(itemName.equals("directions")) {
							recipe.setAddress(item.getString("utf-8"));
						}else if(itemName.equals("step")) {
							Step step = new Step();
							step.setDescription(item.getString("utf-8"));
							steps.add(step);
						}else if(itemName.equals("ingredient_name")) {
							String ingredient_name = item.getString("utf-8");
							materials.add(ingredient_name);
						}else if(itemName.equals("ingredient_note")) {
							String ingredient_note = item.getString("utf-8");
							Mquantities.add(ingredient_note);
						}else {
							System.out.println("�����ֶ�");
						}
					}else {
						if(itemName.equals("picture")) {
							//file �ļ��ϴ�
							//getFieldName�ǻ�ȡ��ͨ���ֶ�nameֵ
							//getName�ǻ�ȡ�ļ���
							String fileName = item.getName();
							
							//�����ļ�����
							String ext = fileName.substring(fileName.lastIndexOf(".")+1);
							if(!ext.equals("jpg")) {
								System.out.println("ͼƬ��������ͼƬֻ����jpg");
								if(recbus.delete(recipe)) {
									System.out.println("��ʼʳ���ѱ�ɾ����");
								}else {
									System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
								}
								String html = "�ļ����˴�ΪͼƬ���ϴ�ʧ�ܣ�<br>ͼƬ��������ͼƬֻ����jpg��<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
								response.getWriter().write(html);
								return ;
							}
							
							if(fileName.contains("\\")) {
		                        //����������ȡ�ַ���
								fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
		                    }
							System.out.println("ԭʼ�ļ�����"+fileName);
							//��ȡ�ļ����ݲ��ϴ�
							//�����ļ�·����ָ���ϴ���λ�ã�������·����������ŵ�workplace�±����̵�һ���ļ���
							String path = "D:\\Course\\Java\\workplace\\RecipeMS\\WebContent\\upload\\recipe\\picture";
							System.out.println("�ļ�����·����"+path);
							//��ȡ������·�����ļ���
							ext = fileName.substring(fileName.lastIndexOf("."));
							fileName = "recipePhoto-" + recipe.getId() + "-" + picNo+ext;
							picNo++;
							
							picUrls.add(fileName);
							
							File file = new File(path,fileName);
							item.write(file);//�ϴ�
							System.out.println(fileName+"�ϴ��ɹ���");
						}
					}
				}
			} catch(FileUploadBase.SizeLimitExceededException e) {
				System.out.println("�ϴ��ļ���С�������ƣ����100MB");
				if(recbus.delete(recipe)) {
					System.out.println("��ʼʳ���ѱ�ɾ����");
				}else {
					System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
				}
				String html = "�ļ����˴�ΪͼƬ���ϴ�ʧ�ܣ�<br>�ϴ��ļ���С�������ƣ����100MB!<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
				response.getWriter().write(html);
				return;
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("ǰ̨��form��multipart���ԣ�");
			System.out.println("ʳ���ϴ�ʧ�ܣ�");
			if(recbus.delete(recipe)) {
				System.out.println("��ʼʳ���ѱ�ɾ����");
			}else {
				System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
			}
			String html = "ʳ���ϴ�ʧ�ܣ�<br>ǰ̨��form��multipart���ԣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
			response.getWriter().write(html);
			return ;
		}
		
		StepBusi stebus = new StepBusi();
		IncludeBusi incbus = new IncludeBusi();
		PictureBusi picbus = new PictureBusi();
		int i = 0;
		
		//�ϴ�ʳ�׻�����Ϣ
		
		recipe.setAuthor(((User)request.getSession().getAttribute("user")).getId());
		System.out.println("����id��" + ((User)request.getSession().getAttribute("user")).getId());
		
		result = recbus.upload(recipe);
		if(result == 1) {
			System.out.println("ʳ�׻�����Ϣ�ϴ��ɹ���");
		}else {
			if(recbus.delete(recipe)) {
				System.out.println("��ʼʳ���ѱ�ɾ����");
			}else {
				System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
			}
			String html = "ʳ�׻�����Ϣ�ϴ�ʧ�ܣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
			response.getWriter().write(html);
			return ;
		}
		
		//�ϴ�����
		Iterator<Step> listStep = steps.iterator();
		Step step = new Step();
		i = 0;
		while(listStep.hasNext()) {
			result = 0;
			step = listStep.next();
			step.setRecipe(recipe.getId());
			step.setSequence(i+1);
			i++;
			result = stebus.upload(step);
			if(result == 1) {
				System.out.println("����  "+i+"  �ϴ��ɹ���");
			}else {
				if(recbus.delete(recipe)) {
					System.out.println("��ʼʳ���ѱ�ɾ����");
				}else {
					System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
				}
				String html = "�����ϴ�ʧ�ܣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
				response.getWriter().write(html);
				return ;
			}
		}
		
		//�ϴ�ʳ��
		Iterator<String> listMate = materials.iterator();
		Iterator<String> Mquantity = Mquantities.iterator();
		Include include = new Include();
		while(listMate.hasNext()) {
			result = 0;
			include.setRecipe(recipe.getId());
			include.setMaterial(Integer.parseInt(listMate.next()));
			include.setQuantity(Mquantity.next());
			result = incbus.upload(include);
			if(result == 1) {
				System.out.println("ʳ��  "+include.getMaterial()+"  �ϴ��ɹ���");
			}else {
				if(recbus.delete(recipe)) {
					System.out.println("��ʼʳ���ѱ�ɾ����");
				}else {
					System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
				}
				String html = "ʳ���ϴ�ʧ�ܣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
				response.getWriter().write(html);
				return ;
			}
		}
		
		//�ϴ�ͼƬ
		Iterator<String> listPic = picUrls.iterator();
		RPicture picture = new RPicture();
		i = 0;
		while(listPic.hasNext()) {
			result = 0;
			picture.setRecipe(recipe.getId());
			picture.setNumber(i);
			i++;
			picture.setUrl("upload/recipe/picture/"+listPic.next());
			result = picbus.upload(picture);
			if(result == 1) {
				System.out.println("ͼƬ  "+picture.getNumber()+"  ¼�����ݿ�ɹ���");
			}else {
				if(recbus.delete(recipe)) {
					System.out.println("��ʼʳ���ѱ�ɾ����");
				}else {
					System.out.println("��ʼʳ��ɾ��ʧ�ܣ�");
				}
				String html = "ͼƬ¼�����ݿ�ʧ�ܣ�<br><a href='recipe_submit.jsp'>�����ϴ�</a><br>";
				response.getWriter().write(html);
				return;
			}
		}
		
		String html = "����ʳ���ϴ��ɹ��������ĵȴ����...<br><a href='recipe_submit.jsp'>�����ϴ�ҳ��</a><br>";
		response.getWriter().write(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
