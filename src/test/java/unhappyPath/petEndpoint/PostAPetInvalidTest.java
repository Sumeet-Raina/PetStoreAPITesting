package unhappyPath.petEndpoint;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.ApiError;

import static utils.PetShopAPI.postInvalidPet;

public class PostAPetInvalidTest {

  @Test
  @DisplayName("Unhappy Path: Empty body returns 500 status code")
  void postPet_EmptyBody_Returns500() {
    Response response = postInvalidPet("{}");

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(500));
  }

  @Test
  @DisplayName("Unhappy Path: Missing required fields returns 422 Validation exception")
  void postPet_MissingName_Returns422() {
    String invalidBody = """
          {
            "id": 15,
            "category": {"id": 1, "name": "Dogs"},
            "photoUrls": null
          }
          """;

    Response response = postInvalidPet(invalidBody);

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(422));

    ApiError error = response.as(ApiError.class);
    MatcherAssert.assertThat(error.getMessage(), Matchers.containsString("Validation exception"));
  }
}
