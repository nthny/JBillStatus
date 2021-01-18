# JBillStatus

> Consulta de comprobantes

![header](https://github.com/nthny/JBillStatus/blob/master/screenshots/header.png?raw=true)

## Descripción

Pequeño programa hecho en java para hacer consulta masiva del estado de facturas, notas de crédito y debito que empiecen con la letra "F" además de poder obtener el archivo zip que contiene el CDR de aceptación o rechazo.

## Comenzando

### Dependencias

JDK 11

### Uso

1. Ingresar con su Clave SOL.

   ![image_1](https://github.com/nthny/JBillStatus/blob/master/screenshots/image_1.gif?raw=true)

2. Preparé un archivo excel con la siguiente estructura.

   | RUC        | TIPO | SERIE | NUMERO |
   | ---------- | ---- | ----- | ------ |
   | 1045529725 | 01   | F001  | 1      |

   - **RUC** del emisor del comprobante.
   - **TIPO** es el código de tipo de documento según el Anexo N° 8: Catalogo de códigos. Para este caso puede tener los valores 01 para facturas, 07 para notas de crédito y 08 para botas de debito.
   - **SERIE** del comprobante  a consultar.
   - **NUMERO** del comprobante a consultar.

3. Arrastre el archivo excel hacia la tabla y espere a que se realice la consulta.

   ![image_2](https://github.com/nthny/JBillStatus/blob/master/screenshots/image_2.gif?raw=true)

## Licencia

TODO