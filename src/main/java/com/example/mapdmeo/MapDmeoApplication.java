package com.example.mapdmeo;

import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.repo.StateRepo;
import com.example.mapdmeo.service.StateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@RequiredArgsConstructor
public class MapDmeoApplication {

	private final StateService stateService;
	private final StateRepo stateRepo;

	@Bean @Transactional @Profile("data")
	public ApplicationRunner runner(){
		return runner ->{
			State state1 = new State();
			state1.setName("Yangon");
			State state2 = new State();
			state2.setName("Mandalay");
			State state3 = new State();
			state3.setName("NayPyiThaw");
			State state4 = new State();
			state4.setName("Chin");
			State state5 = new State();
			state5.setName("Yakhine");
			State state6 = new State();
			state6.setName("Shan");

			stateRepo.save(state1);
			stateRepo.save(state2);
			stateRepo.save(state3);
			stateRepo.save(state4);
			stateRepo.save(state5);
			stateRepo.save(state6);
		};
	}



	public static void main(String[] args) {
		SpringApplication.run(MapDmeoApplication.class, args);
	}


}
