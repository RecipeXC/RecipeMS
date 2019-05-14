package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.RecipeBusi;
import net.sf.json.JSONArray;
import pojo.Recipe;

/**
 * Servlet implementation class SearchRecipe
 */
@WebServlet("/SearchRecipeName")
public class SearchRecipeName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRecipeName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
        //���Ȼ�ÿͻ��˷��͹���������keyword
        String keyword = request.getParameter("keyword");
        RecipeBusi recbus= new RecipeBusi();
        List<String> NameList = new ArrayList<>();
        
        List<Recipe> RecipeList  = recbus.SearchRecipe(keyword);

		for(int i = 0 ; i < RecipeList.size() ; i++) {
			NameList.add(RecipeList.get(i).getName());
		}
        //����json��ʽ
//        System.out.println(JSONArray.fromObject(listData));
        response.getWriter().write(JSONArray.fromObject(NameList).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
