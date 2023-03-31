package com.example.mytodoapp.services

import com.example.mytodoapp.constants.FieldTypes
import com.example.mytodoapp.entities.ui.ValidationCase

object Validator {
    fun validate(data: Map<FieldTypes, ValidationCase>): Map<FieldTypes, Boolean> {
        return data.mapValues {
            val regex = Regex(it.value.pattern)
            !regex.matches(it.value.data)
        }
    }
}