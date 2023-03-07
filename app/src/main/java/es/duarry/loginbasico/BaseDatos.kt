package es.duarry.loginbasico

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import kotlin.collections.ArrayList


val Bdatos = "PetPet"

class BaseDatos(contexto: Context) : SQLiteOpenHelper(contexto, Bdatos, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTabla = "CREATE TABLE usuarios( user TEXT PRIMARY KEY, contra TEXT)"
        db?.execSQL(crearTabla)

        val crearTabla2 = "CREATE TABLE animales( cod Integer PRIMARY KEY autoincrement, nombre TEXT,raza TEXT,sexo TEXT, fechNac String,Dni TEXT, foto String)"
        db?.execSQL(crearTabla2)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    //inserta usuario a bd
    fun insterarUsuario(){
        val bd = this.writableDatabase

        val valores = ContentValues().apply {
            put("user","Duarry")
            put("contra","12345")
        }

        bd.insert("usuarios",null, valores)

    }

    //Inserta animales a la bd
    fun insertarAnimales() {
        val db = writableDatabase
        val nombres = listOf("Rocky", "Toby", "Chispa", "Chiqui", "Scooby", "Goofy", "Luca", "Draco", "Rocky", "Kira", "Lucky")
        val razas = listOf("Caniche", "Bulldog francés", "Golden Retriever", "Husky", "Labrador", "Pastor alemán", "Border Collie", "Chow Chow", "Dachshund", "Dálmata")
        val sexos = listOf("Macho", "Hembra")
        val dniselect = listOf("83418624Q","31484988N","02241214W","70511890P","94101031G","75229757T","09080372H","82961987K","07272450B","26750359W")
        val fotos = listOf("https://images.hola.com/imagenes/mascotas/20190820147813/razas-perros-pequenos-parecen-grandes/0-711-550/razas-perro-pequenos-grandes-a.jpg",
            "https://estaticos-cdn.prensaiberica.es/clip/e1c20447-c530-4f05-90de-4800e6c78b38_16-9-aspect-ratio_default_0.jpg",
            "https://www.petdarling.com/wp-content/uploads/2020/11/razas-de-perros.jpg")
        val animales = mutableListOf<Animales>()


        for (i in 1..10) {
            val nombre = nombres.random()
            val raza = razas.random()
            val sexo = sexos.random()
            val fechNac = LocalDate.now().toString()
            val dni = dniselect.random()
            val foto = fotos.random()
            val animal = Animales(i,nombre, raza, sexo, fechNac,dni,foto)
            animales.add(animal)
        }

        animales.forEach { animal ->
            val valores = ContentValues().apply {
                put("nombre", animal.nombre)
                put("raza", animal.raza)
                put("sexo", animal.sexo)
                put("fechNac", animal.fechNac)
                put("Dni", animal.Dni)
                put("foto", animal.foto)
            }
            db?.insert("animales", null, valores)
        }
    }

    fun verificaUsuario(usuario: String,contra: String): Boolean{
        val bd = this.readableDatabase
        val sql = "SELECT * FROM usuarios WHERE user = '$usuario' and contra= '$contra'"
        val cursor = bd.rawQuery(sql, null)

        if(cursor.count<=0){
            return false
        }
        cursor.close()
        return true
    }

    fun mostrarAnimales(): MutableList<Animales>{
        val lista: MutableList<Animales> = ArrayList()
        val db = this.readableDatabase
        val sql = "SELECT * from animales"
        val resultado = db.rawQuery(sql, null)

        if (resultado.moveToFirst()) {
            do {
                var ani = Animales()

                ani.cod = resultado.getInt(resultado.getColumnIndexOrThrow("cod"))
                ani.nombre = resultado.getString(resultado.getColumnIndexOrThrow("nombre"))
                ani.raza = resultado.getString(resultado.getColumnIndexOrThrow("raza"))
                ani.sexo = resultado.getString(resultado.getColumnIndexOrThrow("sexo"))
                ani.fechNac = resultado.getString(resultado.getColumnIndexOrThrow("fechNac"))
                ani.Dni = resultado.getString(resultado.getColumnIndexOrThrow("Dni"))
                ani.foto = resultado.getString(resultado.getColumnIndexOrThrow("foto"))

                lista.add(ani)

            } while (resultado.moveToNext())
            resultado.close()
            db.close()
            return lista
        }
        return lista
    }

    fun altaAnimal(nombre:String,raza:String,sexo:String,fecha:String,dni:String,foto:String){
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("raza", raza)
            put("sexo", sexo)
            put("fechNac", fecha)
            put("Dni", dni)
            put("foto", foto)
        }
        db?.insert("animales", null, valores)
    }

    fun modificaAnimal(cod: String, nombre: String, raza: String, sexo: String, fechNac: String, dni: String) {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("raza", raza)
            put("sexo", sexo)
            put("fechNac", fechNac)
            put("Dni", dni)
        }

        db.update("animales", valores, "cod = ?" , arrayOf(cod))
        db.close()
    }

    fun borraAnimal(cod: String){
        val db = this.writableDatabase
        db.delete("animales", "cod = ?", arrayOf(cod))
    }

    fun consultaAnimal(cod: String): Animales {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM animales WHERE cod = $cod"
        val cursor = db.rawQuery(selectQuery, null)
        var animal = Animales()

        if (cursor.moveToFirst()) {
            val codigo = cursor.getInt(cursor.getColumnIndexOrThrow("cod"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val raza = cursor.getString(cursor.getColumnIndexOrThrow("raza"))
            val sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"))
            val fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechNac"))
            val dni = cursor.getString(cursor.getColumnIndexOrThrow("Dni"))
            val foto = cursor.getString(cursor.getColumnIndexOrThrow("foto"))

            animal = Animales(codigo, nombre, raza, sexo, fechaNacimiento, dni, foto)
        }
        cursor.close()
        db.close()

        return animal
    }

}