package com.project.study.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDTOTest {

    @Test
    public void 롬복기능테스트() {
        String name = "테스트";
        int amount = 10000;

        HelloResponseDTO helloResponseDTO = new HelloResponseDTO(name, amount);

        assertThat(helloResponseDTO.getName()).isEqualTo("테스트");
        assertThat(helloResponseDTO.getAmount()).isEqualTo(10000);
    }

}