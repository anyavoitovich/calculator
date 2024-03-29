# Калькулятор

Это приложение представляет собой калькулятор для выполнения математических операций на устройствах Android. Он имеет простой интерфейс с использованием `LinearLayout` и `ConstraintLayout`, а также кнопки для ввода цифр, операций и других функций.

## Описание интерфейса

Интерфейс приложения состоит из двух основных текстовых полей (`TextView`), отображающих вводимые пользователем числа и результаты операций. Ввод осуществляется при помощи кнопок с цифрами, операторами и функциями.

## Основные функции

1. **Ввод чисел**: Пользователь может вводить цифры с клавиатуры, используя кнопки с цифрами и кнопку `.` для ввода десятичной точки.

2. **Основные операции**: Пользователь может выполнять базовые математические операции: сложение (`+`), вычитание (`-`), умножение (`×`) и деление (`/`).

3. **Дополнительные функции**: Пользователь может использовать дополнительные функции, такие как очистка (`C`), удаление последней цифры (`⌫`), изменение знака числа (`-/+`), вычисление процентов (`%`) и извлечение квадратного корня (`√`). Также реализованы тригонометрические функции `sin`, `cos`, `tan` и `cot`.

4. **Вычисление**: После ввода математического выражения пользователь может нажать на кнопку `=`, чтобы вычислить результат.

## Обработка ошибок

Приложение реагирует на некоторые ошибочные ситуации, такие как попытка деления на ноль, корень отрицательного числа и другие, и предоставляет соответствующие сообщения об ошибках.

## Основной код

Основная логика калькулятора находится в файле `MainActivity.kt`, где реализованы методы для обработки ввода пользователя, выполнения операций и вычисления результатов.

---

*Примечание: Этот README файл предоставляет общее описание приложения и его функциональности. Дополнительные сведения о реализации и использовании могут быть найдены в комментариях внутри кода.*
