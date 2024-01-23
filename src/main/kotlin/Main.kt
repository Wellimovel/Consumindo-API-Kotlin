import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

fun main() {
    val leitura = Scanner (System.`in`)
    while (true) {
    println("Digite um código de jogo para buscar:")
    val busca = leitura.nextLine()
    val endereco = "https://www.cheapshark.com/api/1.0/games?id=$busca"

    val client:HttpClient = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build()
    val response = client
        .send(request, HttpResponse.BodyHandlers.ofString())
    val json = response.body()
   // println(json)

    var meuJogo: Jogo? = null



    val gson = Gson()

    try {
        if (json.startsWith("[")) {
            // Se o JSON começa com "[" é um array vazio, então tratamos como nenhum jogo encontrado
            println("Jogo Inexistente")
        } else {
            val meuInfoJogo = gson.fromJson(json, infoJogo::class.java)
            val resultado = runCatching {
                 meuJogo = Jogo(
                    meuInfoJogo.info.title,
                    meuInfoJogo.info.thumb
                )
                println(meuJogo)
            }
            resultado.onFailure { println("Jogo Inexistente") }
            resultado.onSuccess {
                println("Deseja inserir descrição? S/N)")
            val opcao = leitura.nextLine()
            if(opcao.equals("s",true)) {
                println("Insira a descrição personalizada para o jogo:")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            }else{
                meuJogo?.descricao = meuJogo?.titulo
            }
                println(meuJogo)
            }
        }
    } catch (e: Exception) {
        println("Erro ao processar o JSON: ${e.message}")
    }
    println("Deseja buscar outro jogo? (s/n)")
    val continuar = leitura.nextLine().toLowerCase()

    if (continuar != "s") {
        break
    }



}}
