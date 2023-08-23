import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import kotlinx.cli.multiple
import kotlinx.cli.optional
import kotlinx.cli.required

@OptIn(ExperimentalCli::class)
object Parser {
    @JvmStatic
    fun main(args: Array<String>) {
        val parser = ArgParser("db-cli")
        parser.subcommands(Backup(), Inspect(), AddColumn())
        parser.parse(args)
    }
}

// Backup Command

class Backup : Subcommand("backup", "Create a backup of the database") {
    val output by option(ArgType.String, shortName = "o", description = "Output file name for the backup").required()
    val format by option(
        ArgType.Choice<Format> { it.name },
        shortName = "f",
        description = "Format for the backup file"
    ).default(Format.SQL)
    val compress by option(ArgType.Boolean, shortName = "c", description = "Compress the backup file").default(false)

    override fun execute() {
        // Logic to create a backup of the database
        println("Creating backup in format $format to file $output. Compress: $compress")
    }

    companion object {
        enum class Format {
            SQL, CSV
        }
    }
}

// Inspect Command
class Inspect : Subcommand("inspect", "Retrieve detailed information about a specific table or column") {
    val table by argument(ArgType.String, description = "Name of the table to inspect")
    val column by argument(ArgType.String, description = "Name of a specific column to inspect").optional()
    val details by option(ArgType.Boolean, description = "Show detailed information").default(false)
    val count by option(
        ArgType.Boolean,
        description = "Display the number of records or distinct values"
    ).default(false)

    override fun execute() {
        // Logic to inspect the table or column
        if (column == null) {
            println("Inspecting table $table. Details: $details, Count: $count")
        } else {
            println("Inspecting column $column of table $table. Details: $details, Count: $count")
        }
    }
}

class AddColumn : Subcommand("add", "Add multiple columns to a table") {
    val table by argument(ArgType.String, description = "Name of the table to modify")
    val columns by option(ArgType.String, shortName = "c", description = "List of columns to add")
        .multiple().required()

    override fun execute() {
        // Logic to add columns to the table
        println("Adding columns $columns to table $table")
    }
}