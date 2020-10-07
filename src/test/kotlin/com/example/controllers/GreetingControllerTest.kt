package com.example.controllers

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

class GreetingControllerTest {

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Rule @JvmField
  val restDocumentation = JUnitRestDocumentation("build/generated-snippets")

  @Before
  fun setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(GreetingController())
      .apply<StandaloneMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
      .alwaysDo<StandaloneMockMvcBuilder>(
        MockMvcRestDocumentation.document(
          "greeting/{method-name}",
          RequestDocumentation.requestParameters(
            RequestDocumentation.parameterWithName("hour").description("時刻: 必須入力(0-24)")
          )
        )
      )
      .build()
  }

  @Test
  @Throws(Exception::class)
  fun GoodMorning下限_正常系() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "5"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good morning"))
  }

  @Test
  @Throws(Exception::class)
  fun GoodMorning上限_正常系() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "15"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good morning"))
  }

  @Test
  @Throws(Exception::class)
  fun GoodNight下限1_正常系() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "0"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good night"))
  }

  @Test
  @Throws(Exception::class)
  fun GoodNight下限2_正常系() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "16"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good night"))
  }

  @Test
  @Throws(Exception::class)
  fun GoodNight上限1_正常系() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "4"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good night"))
  }

  @Test
  @Throws(Exception::class)
  fun doc() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "24"))
      .andExpect(MockMvcResultMatchers.status().isOk)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("good night"))
      .andDo(
        MockMvcRestDocumentation.document(
          "greeting/{method-name}",
          PayloadDocumentation.responseFields(
            PayloadDocumentation.fieldWithPath("greeting").type(JsonFieldType.STRING)
              .description("hourが0-4,16-24の場合はgood night,5-15の場合はgood morning")
          )
        )
      )
  }

  @Test
  @Throws(Exception::class)
  fun 入力値範囲外_下限越え() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "-1"))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
  }

  @Test
  @Throws(Exception::class)
  fun 入力値範囲外_上限超え() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "25"))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
  }

  @Test
  @Throws(Exception::class)
  fun 入力値範囲外_数値外入力() {
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting").param("hour", "123abc"))
      .andExpect(MockMvcResultMatchers.status().isBadRequest)
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
  }
}