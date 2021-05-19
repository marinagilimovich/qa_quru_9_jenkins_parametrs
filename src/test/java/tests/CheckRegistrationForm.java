package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class CheckRegistrationForm extends TestBase{

    Faker faker = new Faker();

    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            emailAddress = faker.internet().emailAddress(),
            userNumber = faker.number().digits(10),
            gender = "Other",
            dateOfBirth = "03",
            monthOfBirth = "May",
            yearOfBirth = "1964",
            subject = "Chemistry",
            hobby = "Music",
            picture = "1.png",
            currentAddress = faker.address().fullAddress(),
            state = "Haryana",
            city = "Karnal";

    @Test
    void successfulFillFormTest() {
        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(emailAddress);
                $(byText(gender)).click();
                $("#userNumber").setValue(userNumber);
            });
            step("Set date", () -> {
                $("#dateOfBirthInput").click();
                $(".react-datepicker__month-select").selectOption(monthOfBirth);
                $(".react-datepicker__year-select").selectOption(yearOfBirth);
                $(".react-datepicker__day--0" + dateOfBirth + ":not(.react-datepicker__day--outside-month)").click();
            });
            step("Set subjects", () -> {
                $("#subjectsInput").setValue(subject).pressEnter();
            });
            step("Set hobbies", () -> {
                $("#hobbiesWrapper").$(byText(hobby)).click();
            });
            step("Upload image", () -> {
                $("#uploadPicture").uploadFromClasspath("img/" + picture);
            });
            step("Set address", () -> {
                $("#currentAddress").setValue(currentAddress);
                $("#state").scrollTo().click();
                $("#stateCity-wrapper").$(byText(state)).click();
                $("#city").click();
                $("#stateCity-wrapper").$(byText(city)).click();
            });
            step("Submit form", () ->
                    $("#submit").click());
        });

        step("Verify successful form submit", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text(firstName + ' ' + lastName), text(emailAddress), text(gender),
                    text(userNumber), text(dateOfBirth + ' ' + monthOfBirth + ',' + yearOfBirth), text(subject),
                    text(hobby), text(picture), text(currentAddress), text(state + " " + city));
        });
    }
}


