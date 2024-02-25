package com.example.mapdmeo;

import com.example.mapdmeo.entity.Address;
import com.example.mapdmeo.entity.Customer;
import com.example.mapdmeo.entity.State;
import com.example.mapdmeo.repo.AddressRepo;
import com.example.mapdmeo.repo.CustomerRepo;
import com.example.mapdmeo.repo.StateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class MapDmeoApplication {

	private final StateRepo stateRepo;
	private final CustomerRepo customerRepo;
	private final AddressRepo addressRepo;

	@Bean
	@Transactional @Profile("data")
	public ApplicationRunner runner(){
		return runner ->{
			State state1 = new State("Yangon",16.8409,96.1735);
			State state2 = new State("Mandalay",21.9588,96.0891);
			State state3 = new State("NayPyiThaw",19.7633,96.0785);
			State state4 = new State("Chin",22.0087,93.5813);
			State state5 = new State("Yakhine",20.1041,93.5813);
			State state6 = new State("Shan",22.0362, 98.1339);

//			No (185/B5) ,Mandalay-Lashio Road , (4) Ward, Pyin Oo Lwin

			Address address1 = new Address("No 3 Kyaik Waing Ward","Yangon","Yangon","11041",16.86699657147835, 96.12551929905736);
			Address address2 = new Address("Office Tower 3,Time City","Kyun Taw Rd","Yangon","11041",16.817058439087884, 96.12953689882758);
			Address address3 = new Address("No. 9/1, Sayar San Rd","Yangon","Yangon","11041",16.861913823207104, 96.2088380861249);
			Address address4 = new Address("Hledan Rd","Yangon","Yangon","11041",16.82600851647623, 96.13007323655538);

			Customer customer1 = new Customer("Andrew","andrew@gmail.com","096764232", LocalDate.of(2002,2,5),
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci alias blanditiis, dolores expedita impedit ipsam libero magnam maiores nemo, nisi provident sint unde. Aperiam illum mollitia rerum sit temporibus!",
					"andrew");

			Customer customer2 = new Customer("Richel","richel@gmail.com","097724232",LocalDate.of(1998,7,21),
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci alias blanditiis, dolores expedita impedit ipsam libero magnam maiores nemo, nisi provident sint unde. Aperiam illum mollitia rerum sit temporibus!",
					"richel");
			Customer customer3 = new Customer("Bruce","bruce@gmail.com","091312312",LocalDate.of(2001,5,24),
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci alias blanditiis, dolores expedita impedit ipsam libero magnam maiores nemo, nisi provident sint unde. Aperiam illum mollitia rerum sit temporibus!",
					"bruce");
			Customer customer4 = new Customer("The Chicken Rice Shop","chicken@gmail.com","01321312",LocalDate.of(1995,3,22),
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium adipisci alias blanditiis, dolores expedita impedit ipsam libero magnam maiores nemo, nisi provident sint unde. Aperiam illum mollitia rerum sit temporibus!",
					"chicken");




			customer1.addAddress(address1);
			customer2.addAddress(address2);
			customer3.addAddress(address3);
			customer4.addAddress(address4);

			stateRepo.saveAll(Arrays.asList(state1, state2, state3, state4, state5, state6));

			// Save customers after setting addresses
			customerRepo.saveAll(Arrays.asList(customer1, customer2, customer3, customer4));

			// Save addresses after setting customers
			addressRepo.saveAll(Arrays.asList(address1, address2, address3, address4));
		};
	}



	public static void main(String[] args) {
		SpringApplication.run(MapDmeoApplication.class, args);
	}


}
