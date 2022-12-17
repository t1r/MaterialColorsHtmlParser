import org.jsoup.Jsoup
import java.io.File
import java.io.InputStream

/**
 * Parse colors from https://m2.material.io/resources/color/
 */
fun main(args: Array<String>) {
    println("=== START ===")

    val colorLabelsFile = File("src/main/resources/color_labels.html")
    val colorLabelsStream: InputStream = colorLabelsFile.inputStream()
    val colorLabelsString = colorLabelsStream.bufferedReader().use { it.readText() }
    val document = Jsoup.parse(colorLabelsString)
    val nameList = getColorGroupNameList(document)

    val palletsFile = File("src/main/resources/pallets.html")
    val palletsStream: InputStream = palletsFile.inputStream()
    val palletsString = palletsStream.bufferedReader().use { it.readText() }
    val palletsDocument = Jsoup.parse(palletsString)
    val result = getColorGroupList(nameList, palletsDocument)

    File("src/main/resources/result.txt").printWriter().use { out ->
        out.println(result)
    }

    println("=== END ===")
}