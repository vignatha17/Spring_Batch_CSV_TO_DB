package com.springbatch.example.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.springbatch.example.model.Data;

@Component

public class Processor implements ItemProcessor<Data,Data> {

    private static final Map<String, String> DEPT_NAMES =new HashMap<>();
    public Processor() {
        DEPT_NAMES.put("101", "Technology");
        DEPT_NAMES.put("102", "Operations");
        DEPT_NAMES.put("103", "Accounts");
    }

	@Override
	    public Data process(Data data) throws Exception {
		 String deptCode =data.getDept();
	        String dept = DEPT_NAMES.get(deptCode);
	        data.setDept(dept);
	      //  user.setTime(new Date());
	        System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
	        return data;
	 }

}
