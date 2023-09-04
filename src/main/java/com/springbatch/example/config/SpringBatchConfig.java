package com.springbatch.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.springbatch.example.model.Data;


@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			ItemReader<Data> itemReader,
			ItemProcessor<Data,Data> itemProcessor,
			ItemWriter<Data> itemWriter
			) {
		Step step=stepBuilderFactory.get("ETL-file-load")
				.<Data,Data>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer( itemWriter)
				.build();
		
		
		 return jobBuilderFactory.get("ETL-Load")
				 .incrementer(new RunIdIncrementer())
				 .start(step)
				 .build();
	}
	
	@Bean
	public FlatFileItemReader<Data> itemReader(){

		FlatFileItemReader<Data> flatFileItemReader=new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource("data.csv"));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
 public LineMapper<Data> lineMapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<Data> defaultLineMapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","dept","salary"});
		BeanWrapperFieldSetMapper<Data> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Data.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper ;
	}

}
