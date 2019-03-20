import { Component, OnInit } from '@angular/core';
import { UserSkill } from '../../Interfaces/UserSkill';
import { SkillTreeService } from '../../services/skill-tree.service';

@Component({
    selector: 'app-skill-tree-page',
    templateUrl: './skill-tree-page.component.html',
    styleUrls: ['./skill-tree-page.component.css']
})
export class SkillTreePageComponent implements OnInit {

    userskills: UserSkill[];

    constructor(private skillTreeService: SkillTreeService) {
    }

    ngOnInit() {
        this.skillTreeService.userskills$.subscribe(userskills => {
            this.userskills = userskills;
        });

        this.skillTreeService.getData();

    }

}
