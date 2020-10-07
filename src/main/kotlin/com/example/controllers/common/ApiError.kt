package controllers.common

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

class ApiError(
  val message: String? = null,

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  val target: String? = null,

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  val details: List<ApiError> = mutableListOf()
) : Serializable