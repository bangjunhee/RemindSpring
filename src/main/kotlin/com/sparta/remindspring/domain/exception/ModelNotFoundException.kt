package com.sparta.remindspring.domain.exception

data class ModelNotFoundException(val model: String, val id: Long?):RuntimeException(
    "Model: $model id: $id 를 찾을 수 없습니다."
)
