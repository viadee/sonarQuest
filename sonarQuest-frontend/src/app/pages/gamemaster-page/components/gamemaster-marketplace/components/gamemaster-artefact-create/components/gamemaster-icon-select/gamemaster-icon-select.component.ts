import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {MatToolbarModule} from '@angular/material/toolbar';
import { GamemasterArtefactCreateComponent } from './../../gamemaster-artefact-create.component';
import { Component, OnInit, Inject } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-gamemaster-icon-select',
  templateUrl: './gamemaster-icon-select.component.html',
  styleUrls: ['./gamemaster-icon-select.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class GamemasterIconSelectComponent implements OnInit {

  icons =  ['ra-arcane-mask', 'ra-all-for-one', 'ra-anvil', 'ra-archer', 'ra-archery-target', "ra-arena", "ra-arrow-cluster", "ra-arrow-flights", "ra-axe", "ra-axe-swing", "ra-barbed-arrow", "ra-barrier", "ra-bat-sword", "ra-battered-axe", "ra-beam-wake", "ra-bear-trap", "ra-bolt-shield",  "ra-bomb-explosion", "ra-bombs", "ra-bone-knife", "ra-boomerang", "ra-boot-stomp", "ra-bowie-knife", "ra-broadhead-arrow", "ra-broken-bone", "ra-broken-shield", "ra-bullets", "ra-cannon-shot", "ra-chemical-arrow", "ra-chain", "ra-circular-saw", "ra-circular-shield", "ra-cluster-bomb", "ra-cracked-helm", "ra-cracked-shield", "ra-croc-sword", "ra-crossbow", "ra-crossed-axes", "ra-crossed-bones", "ra-crossed-pistols"/* , "ra-crossed-sabers" */, "ra-crossed-swords", "ra-daggers", "ra-dervish-swords", "ra-diving-dagger", "ra-drill", "ra-dripping-blade", "ra-dripping-knife", "ra-dripping-sword", "ra-duel", "ra-explosion", "ra-explosive-materials", "ra-eye-shield", "ra-fire-bomb", "ra-fire-shield", "ra-fireball-sword", "ra-flaming-arrow", "ra-flaming-claw", "ra-flaming-trident", "ra-flat-hammer", "ra-frozen-arrow", "ra-gavel", "ra-gear-hammer", "ra-grappling-hook", "ra-grenade", "ra-guillotine", "ra-halberd", "ra-hammer", "ra-hammer-drop", "ra-hand-saw", "ra-harpoon-trident", "ra-helmet", "ra-horns", "ra-heavy-shield", "ra-implosion", "ra-jetpack", "ra-kitchen-knives", "ra-knife", "ra-knight-helmet", "ra-kunai", "ra-large-hammer", "ra-laser-blast", "ra-lightning-sword", "ra-mass-driver", "ra-mp5", "ra-musket", "ra-plain-dagger", "ra-relic-blade", "ra-revolver", "ra-rifle", "ra-round-shield", "ra-scythe", "ra-shield", "ra-shuriken", "ra-sickle", "ra-spear-head", "ra-spikeball", "ra-spiked-mace", "ra-spinning-sword", "ra-supersonic-arrow", "ra-sword", "ra-target-arrows", "ra-target-laser",  "ra-thorn-arrow", "ra-thorny-vine",  "ra-trident", "ra-vest", "ra-vine-whip", "ra-zebra-shield"];

  constructor(
    private dialogRef: MatDialogRef<GamemasterArtefactCreateComponent>,
    @Inject(MAT_DIALOG_DATA) public selectedIcon: string
  ) { }

  ngOnInit() {
  }

  select(icon: string){
    this.dialogRef.close(icon);
  }
}
