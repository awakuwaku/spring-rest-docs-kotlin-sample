package com.example.controllers

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class RequestCriteria(
  @field: Max(24)
  @field: Min(0)
  var hour: Int = 0
)