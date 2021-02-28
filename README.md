<img src="https://github.com/nthny/JBillStatus/blob/master/src/main/resources/img/jbillstatus.ico?raw=true" alt="logo" style="zoom:5%;" />

# JBillStatus

> Consulta de comprobantes

## Descripción

Consulta masiva del estado de facturas, notas de crédito y debito que empiecen con la letra "F" además de obtener el archivo zip que contiene el CDR de aceptación o rechazo.

## Dependencias

- Clave SOL
- OpenJDK 11 o JDK 11

## Uso Y Características

1. Ingrese con su Clave SOL. Puede darle en recordar para guardar el RUC y el Usuario.

   ![image_1](https://github.com/nthny/JBillStatus/blob/master/screenshots/img_1.gif?raw=true)

2. Prepare un archivo excel con la siguiente estructura.

   | RUC        | TIPO | SERIE | NUMERO |
   | ---------- | ---- | ----- | ------ |
   | 1045529725 | 01   | F001  | 1      |

   **RUC** del emisor del comprobante.
   **TIPO** es el código de tipo de documento según el Anexo N° 8: Catálogo de códigos. Puede tomar los valores 01 para facturas, 07 para notas de crédito y 08 para botas de débito.
   **SERIE** del comprobante  a consultar.
   **NUMERO** del comprobante a consultar.

   Con el archivo listo arrastrarlo hacia la tabla, dependiendo del numero de comprobantes puede demorar unos segundos.
      
   ![image_2](https://github.com/nthny/JBillStatus/blob/master/screenshots/img_2.gif?raw=true)

3. Puede filtrar los comprobantes consultados según el CODIGO DE RETORNO o DESCRIPCION DE RETORNO.

   ![image_3](https://github.com/nthny/JBillStatus/blob/master/screenshots/img_3.gif?raw=true)

   El campo estado puede tomar uno de los siguiente valores:

   | TIPO  | CODIGO DE RETORNO | DESCRIPCION DE RETORNO                                       |
   | ----- | ----------------- | ------------------------------------------------------------ |
   | EXITO | 0001              | El comprobante existe y está aceptado.                       |
   | EXITO | 0002              | El comprobante existe pero está rechazado.                   |
   | EXITO | 0003              | El comprobante existe pero está de baja.                     |
   | ERROR | 0004              | Formato de RUC no es válido (debe de contener 11 caracteres numéricos). |
   | ERROR | 0005              | Formato del tipo de comprobante no es válido (debe de contener 2 caracteres). |
   | ERROR | 0006              | Formato de serie inválido (debe de contener 4 caracteres).   |
   | ERROR | 0007              | El numero de comprobante debe de ser mayor que cero.         |
   | ERROR | 0008              | El número de RUC no está inscrito en los registros de la SUNAT. |
   | ERROR | 0009              | EL tipo de comprobante debe de ser (01, 07 o 08).            |
   | ERROR | 0010              | Sólo se puede consultar facturas, notas de crédito y debito electrónicas, cuya serie empieza con "F" |
   | ERROR | 0011              | El comprobante de pago electrónico no existe.                |
   | ERROR | 0012              | El comprobante de pago electrónico no le pertenece.          |

4. Para obtener el CDR de aceptación o rechazo haga doble clic sobre el comprobante y luego elija la ubicación para guardar el archivo zip con el archivo xml. 

   ![image_4](https://github.com/nthny/JBillStatus/blob/master/screenshots/img_4.gif?raw=true)