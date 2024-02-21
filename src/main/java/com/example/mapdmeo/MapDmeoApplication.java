package com.example.mapdmeo;

import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.repo.StateRepo;
import com.example.mapdmeo.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class MapDmeoApplication {

	private final StateService stateService;
	private final StateRepo stateRepo;

	@Bean
	@Transactional @Profile("data")
	public ApplicationRunner runner(){
		return runner ->{
			State state1 = new State(1,"Yangon",16.8409,96.1735);
			State state2 = new State(2,"Mandalay",21.9588,96.0891);
			State state3 = new State(3,"NayPyiThaw",19.7633,96.0785);
			State state4 = new State(4,"Chin",22.0087,93.5813);
			State state5 = new State(5,"Yakhine",20.1041,93.5813);
			State state6 = new State(6,"Shan",22.0362, 98.1339);


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
