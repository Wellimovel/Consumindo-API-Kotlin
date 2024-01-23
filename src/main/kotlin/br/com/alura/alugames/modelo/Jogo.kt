package br.com.alura.alugames.modelo

data class Jogo(var titulo: String,
                val capa: String) {


    var descricao:String? = null
    override fun toString(): String {
        return "Meu br.com.alura.alugames.modelo.Jogo:\n" +
                "Titulo: $titulo \n"+
                "Capa: $capa \n"+
                "Descricao: $descricao"
    }

}