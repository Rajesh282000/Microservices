package com.firewall.inventory_service;

import com.firewall.inventory_service.model.Inventory;
import com.firewall.inventory_service.repository.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(InventoryRepo repository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_13_test");
			inventory.setQuantity(100);
			repository.save(inventory);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone_13pro_test");
			inventory1.setQuantity(10);
			repository.save(inventory1);
		};
	}
}
