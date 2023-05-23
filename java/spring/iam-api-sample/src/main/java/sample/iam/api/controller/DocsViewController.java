package sample.iam.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Docs view controller.
 */
@Controller
public class DocsViewController {

  /**
   * Api docs render string.
   *
   * @return the string
   */
  @GetMapping("/")
  public String apiDocsRender() {
    return "redirect:/docs/index.html";
  }

  /**
   * Message page render string.
   *
   * @return the string
   */
  @GetMapping("/docs")
  public String messagePageRender() {
    return "redirect:/docs/index.html";
  }
}
