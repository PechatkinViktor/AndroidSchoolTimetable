package com.pechatkin.sbt.androidschooltimetable.dataProvider;

import com.pechatkin.sbt.androidschooltimetable.models.Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AndroidSchoolTimetableProvider {

    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

    private List<Lecture> mLectures = Arrays.asList(
            new Lecture(1, "24.09.2019", "Вводное занятие", "Соколов", "Вводная информация курса"),
            new Lecture(2, "26.09.2019", "View, Layouts", "Соколов", "Базовые вью (TextView, ImageView), базовые лэйауты (linear, frame, relative)"),
            new Lecture(3, "28.09.2019", "Drawables", "Соколов", "Drawable, существующие типы, levels, states"),
            new Lecture(4, "01.10.2019", "Activity", "Сафарян", "Жизненный цикл Activity подробно, сохранение кастомных состояний"),
            new Lecture(5, "03.10.2019", "Адаптеры", "Чумак", "RecyclerView: как стало принято теперь"),
            new Lecture(6, "05.10.2019", "UI: практика", "Кудрявцев", "Практическое занятие"),
            new Lecture(7, "08.10.2019", "Custom View", "Кудрявцев", "Теория рисования на Canvas"),
            new Lecture(8, "10.10.2019", "Touch events", "Бильчук", "Теория onTouchEvent, ACTION_CANCEL"),
            new Lecture(9, "12.10.2019", "Сложные жесты", "Соколов", "Мультитач, GestureDetector"),
            new Lecture(10, "15.10.2019", "Layout & Measurement", "Кудрявцев", "onMeasure и все вопросы, связанные с измерением вьюхи для лэйаута"),
            new Lecture(11, "17.10.2019", "Custom ViewGroup", "Кудрявцев", "Роутинг событий, перехват событий onInterceptTouchEvent"),
            new Lecture(12, "19.10.2019", "Анимации", "Чумак", "Практическая демонстрация анимации готовых вью"),
            new Lecture(13, "22.10.2019", "Практика View", "Соколов", "Большое самостоятельное практическое занятие с заданием на написание какой-нибудь интересной вью или группы"),
            new Lecture(14, "24.10.2019", "Фрагменты: база", "Бильчук", "Теория о фрагментах: зачем нужны, как работают, бэкстек, behavior fragments, data fragments; живая демонстрация примеров"),
            new Lecture(15, "26.10.2019", "Фрагменты: практика", "Соколов", "Практическое занятие с переводом прошлого приложения на фрагменты вместо активити"),
            new Lecture(16, "29.10.2019", "Фоновая работа", "Чумак", "Подробно про UI thread, Looper, Handler"),
            new Lecture(17, "31.10.2019", "Абстракции фон/UI", "Леонидов", "Самописный паттерн MVP: теория о том, как нам проще всего абстрагироваться от слоя логики"),
            new Lecture(18, "05.11.2019", "Фон: практика", "Чумак", "Практическое занятие с написанием полезного приложения, используя ранее полученные навыки"),
            new Lecture(19, "07.11.2019", "BroadcastReceiver", "Бильчук", "Теория по BroadcastReceiver"),
            new Lecture(20, "09.11.2019", "Runtime permissions", "Кудрявцев","Пермишены, данные на внешнем накопителе, различные виды внешних каталогов, Environment"),
            new Lecture(21, "12.11.2019", "Service", "Леонидов", "Теория о сервисах, приоритетах процессов, понятие «непустого процесса»"),
            new Lecture(22, "14.11.2019", "Service: практика", "Леонидов", "Написание приложения, скачивающего большой файл на внешний накопитель, не прерывая процесс за счёт перехода в сервис"),
            new Lecture(23, "16.11.2019", "Service: биндинг", "Леонидов", "Теория о взаимодействии процессов через бинд сервиса, AIDL"),
            new Lecture(24, "19.11.2019", "Preferences", "Сафарян", "Теоретическая часть: настройки, их виды, кастомизация"),
            new Lecture(25, "21.11.2019", "SQLite", "Бильчук", "Теория: использование SQLite в приложении Android (базовое, без библиотек, Cursor), обновление версии БД"),
            new Lecture(26, "23.11.2019", "SQLite: Room", "Соколов", "Теория: использование Room"),
            new Lecture(27, "26.11.2019", "ContentProvider", "Сафарян","Теория о провайдерах: зачем нужны, URI, типы данных"),
            new Lecture(28, "28.11.2019", "FileProvider", "Соколов", "Практика: передача наружу, например, сгенерированных картинок, либо статики, как получится сделать по времени"),
            new Lecture(29, "30.11.2019", "Геолокация", "Леонидов", "Теория: виды провайдеров местоположения, последнее известное положение, подписка на обновление местоположений"),
            new Lecture(30, "03.12.2019", "Material", "Чумак", "Компоненты Material Design"),
            new Lecture(31, "05.12.2019", "UI-тесты", "Сафарян", "Espresso"),
            new Lecture(32, "07.12.2019", "Финал", "Соколов", "Большое финальное занятие с закрытием всех оставшихся вопросов")
    );

    public List<Lecture> provideLectures() {
        return new ArrayList<>(mLectures);
    }

    public List<String> providerLectorers() {
        Set<String> lectorersSet = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectorersSet.add(lecture.getLecturer());
        }
        return new ArrayList<>(lectorersSet);
    }

    public List<Lecture> filterBy(String lectorName) {
        List<Lecture> result = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if (lecture.getLecturer().equals(lectorName)) {
                result.add(lecture);
            }
        }
        return result;
    }

    public Lecture getCurrentLecture (Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
        for (Lecture lecture : mLectures) {
            try {
                Date lectureDate = format.parse(lecture.getDate());
                if (lectureDate != null && lectureDate.after(date)) {
                    return lecture;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return mLectures.get(mLectures.size() - 1);
    }
}
