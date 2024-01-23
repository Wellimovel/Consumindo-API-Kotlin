package br.com.alura.alugames.servicos

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class ConsumoApi {

    fun buscarJogo(codigo: String): String {
        val endereco = "https://www.cheapshark.com/api/1.0/games?id=$codigo"
        val client: HttpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder().uri(URI.create(endereco)).build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}
