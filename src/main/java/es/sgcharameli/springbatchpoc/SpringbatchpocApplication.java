package es.sgcharameli.springbatchpoc;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class SpringbatchpocApplication {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	public SpringbatchpocApplication(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory) {

		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItemStep").tasklet((contribution, chunkContext) -> {
			log.debug("The item has been packaged.");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Job deliverPackageJob() {
		return this.jobBuilderFactory.get("deliverPackageJob").start(packageItemStep()).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchpocApplication.class, args);
	}

}
