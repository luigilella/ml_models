/****************************************************************************

Copyright (C) (2003) Luigi Lella

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either  version 3 of the License, or  (at your option)  any later
version.

This program is distributed  in the hope that it will be useful,  but WITHOUT 
ANY WARRANTY, without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License  along with 
this program. If not, see <http://www.gnu.org/licenses/>.

*****************************************************************************/

import java.util.HashMap;
import java.io.*;
import org.jdom.Document;
import org.jdom.Element;

@SuppressWarnings("serial")
public class DataBase implements Serializable
{
  private Document document;
  @SuppressWarnings("rawtypes")
  private HashMap map;

  @SuppressWarnings("rawtypes")
  public DataBase(String subject)
  {
    document = new Document(new Element(subject));
    map = new HashMap();
  }

  public Document getDocument()
  {
    return document;
  }

  @SuppressWarnings("rawtypes")
  public HashMap getMap()
  {
    return map;
  }

  public String getSubject()
  {
    return document.getRootElement().getName();
  }
}
