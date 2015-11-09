package com.tcc.apptcc.activities;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.apptcc.pojos.Avistamento;
import com.tcc.apptcc.pojos.Circunstancia;
import com.tcc.apptcc.pojos.Localizacao;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Pri on 06/11/2015.
 */


public class AvistamentosCard extends CardWithList {
    public AvistamentosCard (Context context) {
        super(context);
    }

    private Avistamento avistamentoRecebido;
    private Circunstancia circunstanciaRecebida;
    private Localizacao localizacaoRecebida;

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(),R.layout.card_avistamentos_header);

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.menu_avistamentos, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_adicionar:
                        //Example: add an item
                        //AvistamentoObject avistamento= new AvistamentoObject(AvistamentosCard.this);
                        //avistamento.endereco = l.getDescricao();
                        //avistamento.circunstanciasAvistamento = c.getDetalhes();
                        //avistamento.dataAvistamento = c.getData();
                        //avistamento.avistamentoIcon = R.drawable.ic_search_black_24dp;
                        //avistamento.setObjectId(avistamento.circunstanciasAvistamento);
                        //mLinearListAdapter.add(avistamento);
                        break;
                    case R.id.action_ver_mapa:
                        //Example: passar para activity mapa
                        //mLinearListAdapter.remove(mLinearListAdapter.getItem(0));
                        break;
                }

            }
        });
        header.setTitle("Weather"); //should use R.string.
        return null;
    }

    @Override
    protected void initCard() {

        //Set the whole card as swipeable
        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected List<ListObject> initChildren() {

        //Init the list
        List<ListObject> mObjects = new ArrayList<ListObject>();

        //Add an object to the list
        AvistamentoObject a= new AvistamentoObject(this);
        a.endereco ="Esteio";
        a.dataAvistamento = "27/09/2015";
        a.circunstanciasAvistamento = "Desapareceu perto de onde estudava";
        a.avistamentoIcon = R.drawable.ic_search_black_24dp;
        a.setObjectId(a.circunstanciasAvistamento); //It can be important to set ad id
        mObjects.add(a);

        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the ui elements inside the item
        TextView endereco = (TextView) convertView.findViewById(R.id.avistamento_card_endereco);
        ImageView icone = (ImageView) convertView.findViewById(R.id.avistamento_card_icon);
        TextView data = (TextView) convertView.findViewById(R.id.avistamento_card_data);

        //Retrieve the values from the object
        AvistamentoObject avistamentoObject= (AvistamentoObject)object;
        icone.setImageResource(avistamentoObject.avistamentoIcon);
        endereco.setText(avistamentoObject.endereco);
        data.setText(avistamentoObject.dataAvistamento);

        return  convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.card_avistamentos_main;
    }



    // -------------------------------------------------------------
    // Weather Object
    // -------------------------------------------------------------

    public class AvistamentoObject extends DefaultListObject{

        public String endereco;
        public String dataAvistamento;
        public String circunstanciasAvistamento;
        public int avistamentoIcon;

        public AvistamentoObject(Card parentCard){
            super(parentCard);
            init();
        }

        private void init(){
            //OnClick Listener
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });

            //OnItemSwipeListener
            setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}

