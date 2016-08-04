/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.artwork.tests.selenium.pages.artwork;

import co.edu.uniandes.csw.artwork.dtos.minimum.ArtworkDTO;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArtworkDetailPage {

    @FindBy(id = "delete-artwork")
    private WebElement deleteBtn;

    @FindBy(id = "edit-artwork")
    private WebElement editBtn;

    @FindBy(id = "list-artwork")
    private WebElement listBtn;

    
    @FindBy(id = "name")
    private WebElement name;
    @FindBy(id = "image")
    private WebElement image;
    @FindBy(id = "price")
    private WebElement price;

    public void list() {
        listBtn.click();
    }

    public void edit() {
        editBtn.click();
    }

    public void delete() {
        deleteBtn.click();
    }

    public ArtworkDTO getData() {
        ArtworkDTO artwork = new ArtworkDTO();        
        artwork.setName(name.getText());
        artwork.setImage(image.getText());
        artwork.setPrice(Long.parseLong(price.getText()));
        return artwork;
    }
}
