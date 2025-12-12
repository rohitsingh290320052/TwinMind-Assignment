package com.example.twinmind_assignment.data

data class GeminiRequest(
    val contents: List<GeminiContent>
)

data class GeminiContent(
    val parts: List<GeminiPart>
)

data class GeminiPart(
    val text: String? = null,
    val inline_data: InlineData? = null
)

data class InlineData(
    val mime_type: String,
    val data: String
)


data class GeminiResponse(val candidates: List<Candidate>?)
data class Candidate(val content: CandidateContent?)
data class CandidateContent(val parts: List<CandidatePart>?)
data class CandidatePart(val text: String?)
