import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/**
 * parse html to List of Color models:
 *
 * @param nameList list of color group names
 * @param document jsoup Document object
 * @return string of kotlin code:
 * ColorGroup(
 *   val title = "Blue",
 *   val items = listOf(
 *    Color(
 *     title="Red 123",
 *     color = 0xFFFFCDD2,
 *    ),
 *   ),
 * )
 */
internal fun getColorGroupList(
    nameList: List<String>,
    document: Document?,
): String {
    document ?: return "listOf()"
    val ulList = document.getElementsByClass("palette-container ng-scope")

    val result = StringBuilder().append("listOf(")
    for (i in 0 until ulList.size) {
        result.append(
            """
        ColorGroup(
          title = "${nameList.getOrNull(i)}",
          items = ${getColorList(ulList[i])},
        ),
        """.trimIndent()
        )
    }
    result.append(")")
    return result.toString()
}

/**
 * List of Color models:
 * @return listOf(
 *   Color(
 *     title="Red 123",
 *     color = 0xFFFFCDD2,
 *   ),
 *   Color(
 *     title= "Red 123",
 *     color = 0xFFFFCDD2,
 *   ),
 * )
 */
private fun getColorList(element: Element?): String {
    element ?: return "listOf()"
    val liList = element.getElementsByTag("li")
    val result = StringBuilder().append("listOf(")
    for (i in 0 until liList.size) {
        getColorModel(liList[i])?.let { modelString ->
            result.append("$modelString,")
        }
    }
    result.append(")")
    return result.toString()
}

/**
 * @return Model Color:
 * Color(
 *   title="Red 123",
 *   color =  0xFFFFCDD2,
 * )
 */
private fun getColorModel(element: Element?): String? {
    element ?: return null
    val nameAttribute = element.attr("aria-label")
    val colorAttribute = element.attr("value")

    return """
    Color(
      title = "$nameAttribute",
      color = 0xFF${colorAttribute.replace("#", "")},
    )
    """.trimIndent()
}