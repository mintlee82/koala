package com.alareubang.koala.web;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.alareubang.koala.web.dto.HelloResponseDto;

public class HelloResponseDtoTest {

	@Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);// 1, 2
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}