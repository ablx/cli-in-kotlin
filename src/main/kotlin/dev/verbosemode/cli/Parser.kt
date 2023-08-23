package dev.verbosemode.cli

import kotlinx.cli.ArgParser

fun main(args: Array<String>) {
    val parser = ArgParser("db-cli")

    parser.parse(args)
}