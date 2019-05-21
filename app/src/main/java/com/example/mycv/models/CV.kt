package com.example.mycv.models

import kotlinx.serialization.Serializable

@Serializable
data class CVInfo(
        val name: String,
        val address: String,
        val summary: String,
        val skills: List<String>,
        val experience: List<ExperienceInfo>
)

@Serializable
data class ExperienceInfo(
        val companyName: String,
        val role: String,
        val date: String,
        val responsibilities: List<String>
)