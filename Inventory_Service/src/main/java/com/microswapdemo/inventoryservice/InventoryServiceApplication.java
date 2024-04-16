package com.microswapdemo.inventoryservice;

import com.microswapdemo.inventoryservice.model.Inventory;
import com.microswapdemo.inventoryservice.repo.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
    @Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
              return args -> {
				  Inventory inventory = new Inventory();
				  inventory.setSkuCode("Iphone_15");
				  inventory.setQuantity(100);

				  Inventory inventory1 = new Inventory();
				  inventory1.setSkuCode("Iphone_15_Red");
				  inventory1.setQuantity(0);

				  inventoryRepository.save(inventory);
				  inventoryRepository.save(inventory1);

			  };
	}
}
