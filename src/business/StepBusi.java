package business;

import java.util.ArrayList;
import java.util.List;

import mapper.StepMapper;
import pojo.Recipe;
import pojo.Step;
import pojo.StepExample;
import util.MybatisSpringUtil;

public class StepBusi {
	
	StepMapper mapper = MybatisSpringUtil.getApplicationContext().getBean(StepMapper.class);
	
	public List<Step> getStep(Recipe recipe){
		
		List<Step> list = new ArrayList<Step>();
		StepExample example = new StepExample();
		StepExample.Criteria criteria = example.createCriteria();
		
		criteria.andRecipeEqualTo(recipe.getId());
		
		list = mapper.selectByExample(example);
		
		return list;
	} 
	
	public int upload(Step step) {
		int r = 0;
		
		r = mapper.insertSelective(step);
		if(r == 1){
	        System.out.println("���ɹ������� "+r+" ��ʳ�ײ��裡");
	    }else{
	        System.out.println("ִ�в���ʳ�ײ������ʧ�ܣ�����");
	    }
		
		return r;
	}

}
