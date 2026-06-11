package com.example.homework.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarkdownText(
    content: String,
    modifier: Modifier = Modifier
) {
    val lines = content.split("\n")
    var inCodeBlock = false
    val codeBlockLines = mutableListOf<String>()
    val rendered = mutableListOf<@Composable () -> Unit>()

    fun flushPending() {
        if (codeBlockLines.isEmpty()) return
        val code = codeBlockLines.joinToString("\n")
        rendered.add {
            Text(
                text = code,
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                color = Color(0xFFE6EDF3),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161B22), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }
        codeBlockLines.clear()
    }

    for (line in lines) {
        if (line.trimStart().startsWith("```")) {
            if (!inCodeBlock) {
                flushPending()
                inCodeBlock = true
            } else {
                inCodeBlock = false
                flushPending()
            }
            continue
        }

        if (inCodeBlock) {
            codeBlockLines.add(line)
            continue
        }

        if (line.isBlank()) {
            rendered.add { Text("") }
            continue
        }

        val trimmed = line.trimStart()

        when {
            trimmed.startsWith("#### ") -> {
                rendered.add {
                    Text(
                        text = parseInlineStyled(trimmed.removePrefix("#### ").trim()),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }
            }
            trimmed.startsWith("### ") -> {
                rendered.add {
                    Text(
                        text = parseInlineStyled(trimmed.removePrefix("### ").trim()),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
                    )
                }
            }
            trimmed.startsWith("## ") -> {
                rendered.add {
                    Text(
                        text = parseInlineStyled(trimmed.removePrefix("## ").trim()),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }
            }
            trimmed.startsWith("# ") -> {
                rendered.add {
                    Text(
                        text = parseInlineStyled(trimmed.removePrefix("# ").trim()),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
                    )
                }
            }
            trimmed.startsWith("- ") || trimmed.startsWith("* ") -> {
                val prefix = if (trimmed.startsWith("- ")) "- " else "* "
                rendered.add {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("  •  ") }
                            append(parseInlineStyled(trimmed.removePrefix(prefix).trim()))
                        },
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }
            trimmed.matches(Regex("""^\d+\.\s.*""")) -> {
                val numEnd = trimmed.indexOf(". ")
                val num = trimmed.substring(0, numEnd)
                val text = trimmed.substring(numEnd + 2)
                rendered.add {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("  $num. ") }
                            append(parseInlineStyled(text.trim()))
                        },
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }
            else -> {
                rendered.add {
                    Text(text = parseInlineStyled(trimmed))
                }
            }
        }
    }

    flushPending()

    Column(modifier = modifier) {
        rendered.forEach { it() }
    }
}

private fun parseInlineStyled(text: String): AnnotatedString {
    return buildAnnotatedString {
        var i = 0
        while (i < text.length) {
            when {
                text[i] == '[' -> {
                    val close = text.indexOf("](", i)
                    if (close != -1) {
                        val urlEnd = text.indexOf(')', close + 2)
                        if (urlEnd != -1) {
                            val linkText = text.substring(i + 1, close)
                            val url = text.substring(close + 2, urlEnd)
                            pushLink(
                                LinkAnnotation.Url(
                                    url = url,
                                    styles = TextLinkStyles(
                                        SpanStyle(
                                            color = Color(0xFF58A6FF),
                                            textDecoration = TextDecoration.Underline
                                        )
                                    )
                                )
                            )
                            withStyle(SpanStyle(
                                color = Color(0xFF58A6FF),
                                textDecoration = TextDecoration.Underline
                            )) {
                                append(linkText)
                            }
                            pop()
                            i = urlEnd + 1
                            continue
                        }
                    }
                    append(text[i])
                    i++
                }
                text.startsWith("**", i) -> {
                    val end = text.indexOf("**", i + 2)
                    if (end != -1) {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(text.substring(i + 2, end))
                        }
                        i = end + 2
                    } else {
                        append(text[i])
                        i++
                    }
                }
                text[i] == '*' && (i == 0 || text[i - 1] != '*') &&
                    (i + 1 < text.length && text[i + 1] != '*') -> {
                    val end = text.indexOf('*', i + 1)
                    if (end != -1) {
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(text.substring(i + 1, end))
                        }
                        i = end + 1
                    } else {
                        append(text[i])
                        i++
                    }
                }
                text[i] == '`' -> {
                    val end = text.indexOf('`', i + 1)
                    if (end != -1) {
                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            background = Color(0x30808080)
                        )) {
                            append(text.substring(i + 1, end))
                        }
                        i = end + 1
                    } else {
                        append(text[i])
                        i++
                    }
                }
                else -> {
                    append(text[i])
                    i++
                }
            }
        }
    }
}
