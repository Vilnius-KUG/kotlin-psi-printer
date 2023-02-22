package printer

import collector.PrintNode
import java.io.PrintWriter

internal class Printer(
    private val writer: PrintWriter,
    private val serializer: PrintNodeSerializer,
) {

    fun print(nodes: Iterable<PrintNode>) {
        nodes.map(serializer::serialize)
            .forEach(writer::println)
        writer.flush()
    }
}