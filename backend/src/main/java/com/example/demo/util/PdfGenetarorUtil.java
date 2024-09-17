package com.example.demo.util;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.swing.text.MaskFormatter;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PdfGenetarorUtil {
    private final TemplateEngine templateEngine;

    public byte[] createPdf(String templateName, Map<String, Object> data) throws DocumentException {
        Context context = new Context();
        context.setVariables(data);


        String htmlContent = templateEngine.process(templateName, context);
        System.out.println(htmlContent);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    public static String formatarCpf(String cpf) throws ParseException {
        MaskFormatter mask = new MaskFormatter("###.###.###-##");
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(cpf);
    }
}
