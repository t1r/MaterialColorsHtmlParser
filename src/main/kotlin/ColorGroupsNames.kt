import org.jsoup.nodes.Document

/**
 * parse html
 * @param document jsoup Document object
 * @return list of color group names listOf("Red", "Blue", ...)
 */
internal fun getColorGroupNameList(document: Document?) : List<String> {
    val liList = document?.getElementsByTag("ul")
        ?.first()
        ?.getElementsByTag("li")
        ?: return emptyList()

    val result = mutableListOf<String>()
    for (i in 0 until liList.size) {
        liList.getOrNull(i)?.let { result.add(it.text()) }
    }
    return result
}
