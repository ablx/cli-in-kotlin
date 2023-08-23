import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.Subcommand
import kotlinx.cli.default
import kotlinx.cli.required

object Parser {
    @JvmStatic
    fun main(args: Array<String>) {
        val parser = ArgParser("db-cli")
        parser.subcommands(Backup())
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
