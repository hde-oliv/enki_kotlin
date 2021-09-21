package com.enki.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.*
import com.enki.models.*

suspend fun fetchIntraInformation(id: String): UserDTO {
    val intraSecret: String = System.getenv("INTRA_SECRET") ?: ""
    val intraID: String = System.getenv("INTRA_ID") ?: ""

    val tokenClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    val intraClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Auth) {
            var tokenInfo: TokenInfo

            bearer {
                loadTokens {
                    tokenInfo = tokenClient.submitForm(
                        url = "https://api.intra.42.fr/oauth/token",
                        formParameters = Parameters.build {
                            append("grant_type", "client_credentials")
                            append("client_id", intraID)
                            append("client_secret", intraSecret)
                        }
                    )
                    BearerTokens(
                        accessToken = tokenInfo.accessToken,
                        refreshToken = tokenInfo.accessToken
                    )
                }
            }
        }
    }

    var userInfo: UserInfo?
    var code: HttpStatusCode = HttpStatusCode.OK
    userInfo = try {
         intraClient.get("https://api.intra.42.fr/v2/users/" + id)
    } catch (e: ClientRequestException) {
        System.err.println(e)
        code = e.response.status
        null
    }

    tokenClient.close()
    intraClient.close()
    return UserDTO(userInfo, code)
}

data class UserDTO(val user: UserInfo?, val code: HttpStatusCode)
