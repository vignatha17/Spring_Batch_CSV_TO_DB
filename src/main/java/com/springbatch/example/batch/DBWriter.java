package com.springbatch.example.batch;

import java.util.List;


import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springbatch.example.model.Data;
import com.springbatch.example.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<Data> {
	  private UserRepository userRepository;

	 @Autowired
	    public DBWriter (UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	
	    public void write(List<? extends Data> data) throws Exception{
	        System.out.println("Data Saved for Users: " + data);
	        userRepository.saveAll(data);
	        }

		

	
	 


}
