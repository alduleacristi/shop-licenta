package com.siemens.ctbav.intership.shop.view.client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@RequestScoped
@ManagedBean(name = "AboutBean")
@URLMappings(mappings = {
		@URLMapping(id = "aboutClient", pattern = "/client/user/About", viewId = "/client/user/about.xhtml"),
		@URLMapping(id = "aboutVisitor", pattern = "/client/About", viewId = "/client/about.xhtml"),

})
public class AboutBean {

	private final String image = "http://localhost:8080/Shop4J/rest/menu/cinesuntem.jpg";
	private final String header = " Am facut fericite milioane de femei care ne-au intrat in magazine, in 20 de ani de moda.Aproape fiecare romanca are o poveste cu o piesa Shop4J care s-a asortat cu zambetul ei larg intr-un moment super special din viata iar toate aceste povesti, nu altceva, fac istoria brandului nostru! Brand romanesc.Ne poarta si generatia de 40-50 ani, si generatia de 20-30 ani. Oferim in fiecare sezon si modele clasice, confortabile, actualizate prin culori fermecatoare, si modele cool, nebunesti, in super tendinte. Mereu pariem ca vor castiga toate bataliile de stil! ;) ";
	private final String footer = "Cateva sute de piese atent selectionate si produse in stoc limitat le gasiti atat in cele 14 magazine fizice Shop4J, cat si in magazinul nostru online. Altele, cateva mii, care nu incap offline :), le gasiti exclusiv online. Ele sunt produse dupa ce primim comenzile voastre pe net, la preturi uimitoare odata ce eliminam toate costurile asociate unui magazin brick & mortar.Online veti gasi si piese alese cu grija de stilistii nostri de la parteneri de incredere, producatori de moda interni sau internationali foarte valorosi. Prieteni. Tot mai multi!Ramaneti aproape de TinaR pe Facebook, locul in care ne adunam zilnic la sueta, locul in care ne jucam, radem, filosofam, ne-ndemnam la noutati, reduceri si alte placeri! Si neaparat tineti minte ca nothing makes a woman more beautiful than the belief that she is beautiful, vorba Sophiei Loren. ";

	public String getHeader() {
		return header;
	}

	public String getFooter() {
		return footer;
	}

	public String getImage() {
		return image;
	}

}
