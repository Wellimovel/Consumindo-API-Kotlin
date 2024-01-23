package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Jogo
import br.com.alura.alugames.modelo.infoJogo
import br.com.alura.alugames.servicos.ConsumoApi
import com.google.gson.Gson
import java.util.*

fun main() {
    val leitura = Scanner(System.`in`)
    val gson = Gson()
    val consumoApi = ConsumoApi()

    while (true) {
        try {
            println("Digite um código de jogo para buscar:")
            val busca = leitura.nextLine()
            val json = consumoApi.buscarJogo(busca)

            var meuJogo: Jogo? = null

            if (json.startsWith("[")) {
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
                    println("Deseja inserir descrição? (S/N)")
                    val opcao = leitura.nextLine()

                    if (opcao.equals("s", true)) {
                        println("Insira a descrição personalizada para o jogo:")
                        val descricaoPersonalizada = leitura.nextLine()
                        meuJogo?.descricao = descricaoPersonalizada
                    } else {
                        meuJogo?.descricao = meuJogo?.titulo
                    }

                    println(meuJogo)
                }
            }
        } catch (e: Exception) {
            println("Erro ao processar o JSON: ${e.message}")
        }

        println("Deseja buscar outro jogo? (S/N)")
        val continuar = leitura.nextLine().toLowerCase()

        if (continuar != "s") {
            break
        }
    }
}
