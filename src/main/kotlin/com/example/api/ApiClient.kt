package  com.example.api
import com.intellij.openapi.project.Project
import org.apache.http.HttpEntity
import org.apache.http.HttpHeaders
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.IOException

object ApiClient {

    private const val BEHAVIOURS_API_URL = "https://dev-ai-service.gocodeo.com/api/v1/generate_behaviours/"
    private const val TEST_CASES_API_URL = "https://dev-ai-service.gocodeo.com/api/v1/generate_test_cases/"

    @Throws(IOException::class)
    fun generateBehaviours(project: Project) {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTgwMjc5MzgsImlhdCI6MTcxNTQzNTkzOCwic3ViIjoiNjUxYWUxY2NkOTZmMjAzOTdkNTRkNGY4IiwibmFtZSI6IkphdGluIEdhcmciLCJpc192ZXJpZmllZCI6dHJ1ZX0.BEIEaCgXmS4WIxwtOpQi9kVKnIg9tRS_Ru1c4Qvl2_4" // Replace with your actual token
        val requestBody = """
            {
                "code": {
                    "main": {
                        "code": "class CreditCard { ... }",
                        "file": "creditcard.py"
                    },
                    "imports": ["import os", "import sys"]
                },
                "language": "python",
                "framework": "unittest",
                "component": "CreditCard",
                "behaviour_type": "Edge and Negative Cases"
            }
        """.trimIndent()

        makePostRequest(BEHAVIOURS_API_URL, token, requestBody)
    }

    @Throws(IOException::class)
    fun generateTestCases(project: Project) {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTgwMjc5MzgsImlhdCI6MTcxNTQzNTkzOCwic3ViIjoiNjUxYWUxY2NkOTZmMjAzOTdkNTRkNGY4IiwibmFtZSI6IkphdGluIEdhcmciLCJpc192ZXJpZmllZCI6dHJ1ZX0.BEIEaCgXmS4WIxwtOpQi9kVKnIg9tRS_Ru1c4Qvl2_4" // Replace with your actual token
        val requestBody = """
            {
                "behaviours": [
                    "1-Initialize CreditCard object",
                    "2-Make payment within credit limit",
                    "3-Make purchase within credit limit",
                    "4-Check balance",
                    "5-Display card information"
                ],
                "language": "Python",
                "framework": "unitest",
                "behaviour_type": "Happy Path",
                "code": {
                    "main": {
                        "code": "class CreditCard { ... }",
                        "file": "creditcard.py"
                    },
                    "imports": ["import os", "import sys"]
                },
                "component": "CreditCard"
            }
        """.trimIndent()

        makePostRequest(TEST_CASES_API_URL, token, requestBody)
    }

    @Throws(IOException::class)
    private fun makePostRequest(apiUrl: String, token: String, requestBody: String) {
        val httpClient: HttpClient = HttpClients.createDefault()
        val httpPost = HttpPost(apiUrl)

        // Set headers
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")

        // Set request body
        httpPost.entity = StringEntity(requestBody)

        // Execute HTTP request
        val response: HttpResponse = httpClient.execute(httpPost)
        val responseEntity: HttpEntity? = response.entity

        // Read response content
        if (responseEntity != null) {
            val responseString = EntityUtils.toString(responseEntity)
            println("API Response:")
            println(responseString)
        } else {
            println("No response from API")
        }
    }
}
